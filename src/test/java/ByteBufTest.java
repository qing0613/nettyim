import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Description
 * @Author ytq
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/21
 */
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);


        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        // 容量API
        // bytebuf底层占用了多少字节的内容
        System.out.println("capacity(): " + buffer.capacity());
        // bytebuf底层最大能占用多少字节内容
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        // 当前读指针readerIndex
        System.out.println("readerIndex(): " + buffer.readerIndex());
        // 表示bytebuf当前可读字节数 它的值等于 writerIndex-readerIndex
        System.out.println("readableBytes(): " + buffer.readableBytes());
        // bytebuf是否可读，writerIndex-readerIndex为0，不可读
        System.out.println("isReadable(): " + buffer.isReadable());
        // 当前写指针writerIndex
        System.out.println("writerIndex(): " + buffer.writerIndex());
        // 可写字节数，它的值等于capacity-writerIndex
        System.out.println("writableBytes(): " + buffer.writableBytes());
        // bytebuf是否可写，capacity-writerIndex为0,不可写
        System.out.println("isWritable(): " + buffer.isWritable());
        // 最大可写字节数，maxCapacity-writerIndex
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
