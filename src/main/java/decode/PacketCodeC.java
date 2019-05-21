package decode;

import io.netty.buffer.ByteBuf;
import model.Command;
import model.packet.LoginRequestPacket;
import model.packet.Packet;
import model.serializer.Serializer;
import model.serializer.SerializerAlgorithm;

/**
 * @Description 解码
 * @Author ytq
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/21
 */
public class PacketCodeC {

    public final  static PacketCodeC INSTANCE = new PacketCodeC();

    private void PachetCodeC(){
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        if(serializeAlgorithm == SerializerAlgorithm.JSON){
            return Serializer.DEFAULT;
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if(command == Command.LOGIN_REQUEST){
            return  LoginRequestPacket.class;
        }
        return null;
    }
}
