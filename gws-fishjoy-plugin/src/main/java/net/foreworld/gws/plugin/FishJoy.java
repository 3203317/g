package net.foreworld.gws.plugin;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.fishjoy.model.Bullet;
import net.foreworld.fishjoy.model.BulletBlast;
import net.foreworld.fishjoy.service.FishjoyService;
import net.foreworld.gws.protobuf.Common.ErrorProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.gws.protobuf.Fishjoy.FishjoyBulletBlastProtobuf;
import net.foreworld.gws.protobuf.Fishjoy.FishjoyBulletProtobuf;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class FishJoy extends BasePlugin {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Resource
	private FishjoyService fishjoyService;

	private static final Logger logger = LoggerFactory.getLogger(FishJoy.class);

	@JmsListener(destination = "qq3203317.5001")
	public void shot(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

			FishjoyBulletProtobuf fbp = FishjoyBulletProtobuf.parseFrom(req.getData());

			Bullet bullet = new Bullet();
			bullet.setId(fbp.getId());
			bullet.setLevel(fbp.getLevel());
			bullet.setSpeed(fbp.getSpeed());
			bullet.setX(fbp.getX());
			bullet.setY(fbp.getY());

			ResultMap<List<Receiver<Bullet>>> map = fishjoyService.shot(sender.getServerId(), sender.getChannelId(),
					bullet);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode(map.getCode());
				err.setMsg(map.getMsg());

				resp.setMethod(req.getMethod());
				resp.setError(err);

				rec.setData(resp);
				rec.setReceiver(sender.getChannelId());

				// 消息回传给自己
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
						rec.build().toByteArray());
				return;
			}

			List<Receiver<Bullet>> _list = map.getData();

			if (null == _list) {
				return;
			}

			int j = _list.size();

			if (0 == j) {
				return;
			}

			resp.setMethod(5002);

			FishjoyBulletProtobuf.Builder _fbpb = FishjoyBulletProtobuf.newBuilder();

			for (int i = 0; i < j; i++) {
				Receiver<Bullet> _receiver = _list.get(i);

				Bullet _b = _receiver.getData();

				_fbpb.setId(_b.getId());
				_fbpb.setLevel(_b.getLevel());
				_fbpb.setX(_b.getX());
				_fbpb.setY(_b.getY());
				_fbpb.setSpeed(_b.getSpeed());
				_fbpb.setSender(_b.getSender());

				resp.setData(_fbpb.build().toByteString());

				rec.setData(resp);
				rec.setReceiver(_receiver.getChannel_id());

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
						rec.build().toByteArray());
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.5003")
	public void blast(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

			FishjoyBulletBlastProtobuf fbbp = FishjoyBulletBlastProtobuf.parseFrom(req.getData());

			BulletBlast bb = new BulletBlast();
			bb.setX(fbbp.getX());
			bb.setY(fbbp.getY());

			ResultMap<List<Receiver<BulletBlast>>> map = fishjoyService.blast(sender.getServerId(),
					sender.getChannelId(), bb);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode(map.getCode());
				err.setMsg(map.getMsg());

				resp.setMethod(req.getMethod());
				resp.setError(err);

				rec.setData(resp);
				rec.setReceiver(sender.getChannelId());

				// 消息回传给自己
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
						rec.build().toByteArray());
				return;
			}

			List<Receiver<BulletBlast>> _list = map.getData();

			if (null == _list) {
				return;
			}

			int j = _list.size();

			if (0 == j) {
				return;
			}

			resp.setMethod(5004);

			FishjoyBulletBlastProtobuf.Builder _fbbpb = FishjoyBulletBlastProtobuf.newBuilder();

			for (int i = 0; i < j; i++) {
				Receiver<BulletBlast> _receiver = _list.get(i);

				BulletBlast _bb = _receiver.getData();

				_fbbpb.setX(_bb.getX());
				_fbbpb.setY(_bb.getY());

				resp.setData(_fbbpb.build().toByteString());

				rec.setData(resp);
				rec.setReceiver(_receiver.getChannel_id());

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
						rec.build().toByteArray());
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}