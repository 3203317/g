package net.foreworld.gws.plugin;

import org.springframework.stereotype.Component;

import net.foreworld.gws.annotation.Action;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Component
public class FishJoy {

	@Action(id = 266)
	public ReceiverProtobuf shot(SenderProtobuf sender) {
		return null;
	}

}
