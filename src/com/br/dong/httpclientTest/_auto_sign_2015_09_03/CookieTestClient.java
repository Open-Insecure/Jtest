package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.*;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-10
 * Time: 20:33
 */
public class CookieTestClient {
    public DefaultHttpClient client = new DefaultHttpClient();
    public static final String HEAD_USER_AGENT = "User-Agent";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36 AlexaToolbar/alxg-3.1";
    public static final String HEAD_REFER = "Referer";
    public static final String PROXY_HOST = "127.0.0.1";
    public static final int PROXY_PORT = 8087;
    public static final Pattern REF_REG = Pattern
            .compile("(^[a-zA-Z]+://[^/]+)[/]?");
    public static final String[] DATE_PATTERNS = new String[] {
            "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z",
            "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z",
            "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z",
            "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z",
            "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z",
            "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z",
            "E, dd-MMM-yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz" };


    public CookieTestClient() {
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);

        class LenientCookieSpec extends BrowserCompatSpec {
            public LenientCookieSpec() {
                super();
                registerAttribHandler(ClientCookie.EXPIRES_ATTR,
                        new BasicExpiresHandler(DATE_PATTERNS) {
                            @Override
                            public void parse(SetCookie cookie, String value)
                                    throws MalformedCookieException {
                                value = StringUtils.replace(value, "\"", "");
                                super.parse(cookie, value);
                            }
                        });
            }
        }
        client.getCookieSpecs().register("chinasource",
                new CookieSpecFactory() {
                    public CookieSpec newInstance(HttpParams params) {
                        return new LenientCookieSpec();
                    }
                });
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                "chinasource");
        client.getParams().setParameter( CoreConnectionPNames.SO_TIMEOUT, 30000);
    }
    public InputStream get(String url) throws Exception {
        return get(url, null);
    }
    public InputStream get(String url, HttpParams params) throws Exception {
        if (StringUtils.isBlank(url))
            return null;
        HttpGet get = new HttpGet(url);
        try {
            get.addHeader(HEAD_USER_AGENT, USER_AGENT);
            get.addHeader(HEAD_REFER, refer(url));

            if (null != params)
                get.setParams(params);
            HttpResponse response = client.execute(get);
            if (null != response) {
                int code = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (code == HttpStatus.SC_OK && null != entity) {
                    InputStream in = entity.getContent();
                    try {
                        return IOUtils.toBufferedInputStream(entity
                                .getContent());
                    } finally {
                        IOUtils.closeQuietly(in);
                    }
                }
                if (code == HttpStatus.SC_MOVED_TEMPORARILY
                        || code == HttpStatus.SC_MOVED_PERMANENTLY
                        || code == HttpStatus.SC_SEE_OTHER
                        || code == HttpStatus.SC_TEMPORARY_REDIRECT) {
                    String nurl = response.getLastHeader("Location").getValue();
                    System.out.println(nurl);
                    return get(nurl, null);
                }
            }
        } finally {
            get.releaseConnection();
            client.getConnectionManager().shutdown();
        }
        return null;
    }

    public InputStream post(String url, FormBodyPart... parts) throws Exception {
        if (StringUtils.isBlank(url))
            return null;
        HttpPost post = new HttpPost(url);
        try {
            post.addHeader(HEAD_USER_AGENT, USER_AGENT);
            post.addHeader(HEAD_REFER, refer(url));
            MultipartEntity enty = new MultipartEntity();
            if (null != parts)
                for (FormBodyPart part : parts)
                    enty.addPart(part);
            post.setEntity(enty);
            HttpResponse response = client.execute(post);
            if (null != response) {
                int code = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (code == HttpStatus.SC_OK && null != entity) {
                    InputStream in = entity.getContent();
                    try {

                        return IOUtils.toBufferedInputStream(entity
                                .getContent());
                    } finally {
                        IOUtils.closeQuietly(in);
                    }
                }
            }
        } finally {
            post.releaseConnection();
            client.getConnectionManager().shutdown();
        }
        return null;
    }

    public static String refer(String url) {
        Matcher matcher = REF_REG.matcher(url);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    @SuppressWarnings("deprecation")
    public CookieTestClient proxy() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    // TODO Auto-generated method stub
                    return null;
                }
            };
            try {
                ctx.init(null, new TrustManager[] { tm }, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = client.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
        client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        return this;
    }

    public static void main(String[] args) throws Exception {
        System.out
                .println(IOUtils.toString(new CookieTestClient()
                        .get("http://www.zzrhmy.com/Register.asp?id=a1-15574")));
    }

}
