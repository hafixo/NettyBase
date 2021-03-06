package org.pavelsumarokov.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Initializes pipeline with decoder for HTTP request
 * and encoder for HTTP response included into Server Codec.
 *
 * Adds Aggregator to receive only full HTTP requests.
 *
 * Finally it adds automatically configured and injected
 * handler for actual processing of requests.
 */
public abstract class HttpChannelInitializer extends ChannelInitializer<Channel> {

    private static final int MAX_CONTENT_LENGTH = 512 * 1024;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(MAX_CONTENT_LENGTH));
        pipeline.addLast("handler", createHandler());
    }

    protected abstract ChannelHandler createHandler();
}
