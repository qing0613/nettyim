package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import server.handler.FirstServerHandler;


/**
 * @Description
 * @Author ytq
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/20
 */
public class NettyServer {

    public static void main(String[] args) {
        // 引导类，进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 创建2个NioEventLoopGroup
        // 表示监听端口，接口新连接的线程组，相当于老板接活
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 表示链接数据处理的线程组，相当于干活的员工
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap
                // 配置两大线程组
                .group(bossGroup, workerGroup)
                // 制定服务端IO模型为NIO
                .channel(NioServerSocketChannel.class)
                // 定义后续每条连接的数据读写，业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
                    }
                });
                // 绑定端口
        bind(serverBootstrap, 8081);
    }

    /**
     * 自定义端口绑定  递增绑定
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("绑定端口"+ port +"成功");
                }else {
                    System.out.println("绑定端口" + (port) +"失败");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
