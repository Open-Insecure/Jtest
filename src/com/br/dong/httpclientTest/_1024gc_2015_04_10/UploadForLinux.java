package com.br.dong.httpclientTest._1024gc_2015_04_10;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-5-28
 * Time: 16:13
 * ����skydrive�вɼ���������Ϣ������
 */
public class UploadForLinux {


    public static void main(String[] args) {
        int type = 0; // Ҫ��������վ
        while(true){
            System.out.println("please chose website:[1 for xinbali,2 for mmhose,3 for yuhuawangchao]");// 1��ʾ�����°��� 2 mmhouse 3��������
            type = readKeyboard();
            // У��
            if (!checkType(type)) {
                //����Ƿ������½���ѭ��
                System.out.println("Enter illegal, please re-enter");
                continue;
            }else{
                break;
            }
        }
        //У��Ϸ�����ʼ����
        System.out.println("start thread ..");
    }
    /**
     * Ѻ������У��
     * @param type ����
     * @return true�������Ҫ��false��������
     */
    public static boolean checkType(int type) {
        if (type == 1 || type == 2 ||type==3) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * ��ȡ�û�����
     * @return  ����������������ʽ�Ƿ��򷵻�0
     */
    public static int readKeyboard() {
        try {
            // ����������
            byte[] b = new byte[1024];
            // ��ȡ�û����뵽����b�У�
            // ��ȡ���ֽ�����Ϊn
            int n = System.in.read(b);
            // ת��Ϊ����
            String s = new String(b, 0, n - 1);
            int num = Integer.parseInt(s);
            return num;
        } catch (Exception e) {}
        return 0;
    }
}
