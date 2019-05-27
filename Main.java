import redis.clients.jedis.Jedis;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {

            Jedis jedis = new Jedis("127.0.0.1",6379);
            jedis.auth("123456");
            String redisKey= "test";
            RedisBitSet redisBitSet = new RedisBitSet(jedis,redisKey);
            BloomFilter bloomFilter = new BloomFilter(Integer.MAX_VALUE,redisBitSet);
            //数据准备
//            FileReader reader = new FileReader("D:/a/uuidFile");
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            String str = "";
//            while ((str = bufferedReader.readLine()) != null) {
//                System.out.println(str + " = "+ System.currentTimeMillis());
//                bloomFilter.add(str);
//            }
//            bufferedReader.close();
//            reader.close();

//            System.out.println(Integer.MAX_VALUE);
//            System.out.println(Long.MAX_VALUE);

            //网络通信
            ServerSocket serverSocket = new ServerSocket(9098);
            Socket socket = serverSocket.accept();
            int count =0 ;
            while (true) {
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));
                count++;
                String line = input.readLine();

                boolean result = bloomFilter.contains(line);
                System.out.println(" 客户端第 ["+ count +"]次发来消息= "+line +" 消息是否存在 = " + result);
                if (line == null || line.equals("null")|| "".equals(line)) {
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
