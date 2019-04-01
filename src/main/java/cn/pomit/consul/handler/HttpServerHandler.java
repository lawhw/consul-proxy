package cn.pomit.consul.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.pomit.consul.http.HttpRequestMessage;
import cn.pomit.consul.http.HttpResponseMessage;
import cn.pomit.consul.http.res.ResCode;
import cn.pomit.consul.http.res.ResType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	private ResourceServerHandler resourceHandler = null;
	private final Log log = LogFactory.getLog(getClass());

	public HttpServerHandler(ResourceServerHandler resourceHandler) {
		this.resourceHandler = resourceHandler;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
		try {

			HttpRequest httpRequest = (HttpRequest) msg;
			HttpRequestMessage httpRequestMessage = new HttpRequestMessage(httpRequest);
			httpRequestMessage.parseRequest();

			log.debug("收到请求：" + httpRequestMessage.getUrl());
			HttpResponseMessage httpResponseMessage = httpRequestMessage.getReponse();

			httpResponseMessage = resourceHandler.handle(httpRequestMessage);
			if (httpResponseMessage == null) {
				httpResponseMessage = new HttpResponseMessage();
				httpResponseMessage.setResType(ResType.TEXT.getValue());
				httpResponseMessage.setResCode(ResCode.INTERNAL_ERROR.getValue());
				httpResponseMessage.setMessage("内部错误！");
			}
			log.debug("响应数据：" + httpResponseMessage);
			ctx.writeAndFlush(httpResponseMessage);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getStackTrace()[0] + "---" + e.getMessage());
			HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
			httpResponseMessage.setResType(ResType.TEXT.getValue());
			httpResponseMessage.setResCode(ResCode.INTERNAL_ERROR.getValue());
			httpResponseMessage.setMessage(e.getMessage());
			ctx.writeAndFlush(httpResponseMessage);
		}
	}
}