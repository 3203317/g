package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.model.Notice;
import net.foreworld.model.ResultMap;
import net.foreworld.service.NoticeService;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class NoticeServiceTest extends BasicTest {

	@Resource
	private NoticeService noticeService;

	@Test
	public void test_findBySysCfg() {
		List<Notice> list = noticeService.findByNotice(null, 1,
				Integer.MAX_VALUE);
		System.out.println("-----" + list.size());
	}

	@Test
	public void test_saveNew() {
		Notice entity = new Notice();
		entity.setContent("content");
		entity.setTitle("title");
		entity.setUser_id("user_id");

		ResultMap<Notice> map = noticeService.saveNew(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_editInfo() {
		Notice entity = new Notice();
		entity.setId("289490d1bde64aaaa58c64bed93ba5cb");
		entity.setContent("1");
		entity.setTitle("2");
		entity.setUser_id("3");

		ResultMap<Void> map = noticeService.editInfo(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_remove() {
		ResultMap<Void> map = noticeService
				.remove("289490d1bde64aaaa58c64bed93ba5cb");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_viewById() {
		Notice notice = noticeService
				.viewById("d575c5a7788e4650927a04580fc4b34a");
		System.out.println("-----" + notice.getView_count());
	}

}
