package model.packet;

import lombok.Data;

/**
 * @Description
 * @Author ytq
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/21
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
