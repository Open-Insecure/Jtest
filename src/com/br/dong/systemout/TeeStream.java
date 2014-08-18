package com.br.dong.systemout;

import java.io.PrintStream;

/** 
 * @author  hexd
 * 创建时间：2014-6-3 上午11:08:01 
 * 类说明 
 */
public class TeeStream extends PrintStream {
    PrintStream out;
    public TeeStream(PrintStream out1, PrintStream out2) {
        super(out1);
        this.out = out2;
    }
    public void write(byte buf[], int off, int len) {
        try {
            super.write(buf, off, len);
            out.write(buf, off, len);
        } catch (Exception e) {
        }
    }
    public void flush() {
        super.flush();
        out.flush();
    }
}
