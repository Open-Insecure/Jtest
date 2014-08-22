package com.br.dong.scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-22
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 *  掷骰子游戏实现
 */
public class DiceGame {
    public static void main(String[] args) {
        int money = 1000; //初始金钱数量
        int diceNum = 0; // 掷出的骰子数值和
        int type = 0; // 玩家押的大小
        int cMoney = 0; // 当前下注金额
        boolean success; // 胜负
        // 游戏过程
        while (true) {
            // 输入大小
            System.out.println("请押大小(1代表大，2代表小):");
            type = readKeyboard();
            // 校验
            if (!checkType(type)) {
                System.out.println("输入非法，请重新输入！");
                continue;
            }
            // 输入下注金额
            while(true){
                System.out.println("你当前的金钱数量是"
                        + money + "请下注:");
                cMoney = readKeyboard();
                // 校验
                if (!checkCMoney(money,cMoney)) {
                    System.out.println("输入非法，请重新输入！");
                    continue;
                }else{
                    break;
                }
            }
            // 掷骰子
            diceNum = doDice();
            // 判断胜负
            success = isSuccess(type,diceNum);
            // 金钱变化
            money = changeMoney(money,success,cMoney);
            // 游戏结束
            if(isEnd(money)){
                System.out.println("你输了，bye！");
                break;
            }
        }
    }

    /**
     * 读取用户输入
     * @return 玩家输入的整数，如果格式非法则返回0
     */
    public static int readKeyboard() {
        try {
            // 缓冲区数组
            byte[] b = new byte[1024];
            // 读取用户输入到数组b中，
            // 读取的字节数量为n
            int n = System.in.read(b);
            // 转换为整数
            String s = new String(b, 0, n - 1);
            int num = Integer.parseInt(s);
            return num;
        } catch (Exception e) {}
        return 0;
    }

    /**
     * 押的类型校验
     * @param type 类型
     * @return true代表符合要求，false代表不符合
     */
    public static boolean checkType(int type) {
        if (type == 1 || type == 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验下注金额是否合法
     * @param money   玩家金钱数
     * @param cMoney 下注金额
     * @return true代表符合要求，false代表不符合要求
     */
    public static boolean checkCMoney(int money, int cMoney) {
        if (cMoney <= 0) {
            return false;
        } else if (cMoney <= money) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 掷骰子
     * @return 骰子的数值之和
     */
    public static int doDice() {
        int n = (int) (Math.random() * 6) + 1;
        int n1 = (int) (Math.random() * 6) + 1;
        int n2 = (int) (Math.random() * 6) + 1;
        // 输出随机结果
        System.out.println("庄家开：" + n + " " + n1 + " " + n2);
        return n + n1 + n2;
    }

    /**
     * 胜负判断
     * @param type 用户输入类型
     * @param diceNum   骰子点数
     * @return true代表赢，false代表输
     */
    public static boolean isSuccess(int type, int diceNum) {
        // 计算庄家类型
        int bankerType = 0;
        if (diceNum <= 9) {
            bankerType = 2;
            System.out.println("庄家开小!");
        } else {
            bankerType = 1;
            System.out.println("庄家开大!");
        }
        if (bankerType == type) { // 赢
            return true;
        } else { // 输
            return false;
        }
    }

    /**
     * 金钱变化
     * @param money 用户钱数
     * @param success 胜负
     * @param cMoney 下注金额
     * @return 变化以后的金钱
     */
    public static int changeMoney(int money, boolean success, int cMoney) {
        if (success) {
            money += cMoney;
        } else {
            money -= cMoney;
        }
        System.out.println("剩余金额：" + money);
        return money;
    }

    /**
     * 判断游戏是否结束
     * @param money 玩家金钱
     * @return true代表结束
     */
    public static boolean isEnd(int money) {
        return money <= 0;
    }
}
