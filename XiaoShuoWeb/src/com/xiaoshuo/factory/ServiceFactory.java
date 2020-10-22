package com.xiaoshuo.factory;

import com.xiaoshuo.service.*;

public final class ServiceFactory {
	private ServiceFactory() {
	}

	public static Object createServiceObject(String className) {
		Object obj = null;
		switch (className.toUpperCase()) {
		case "USER":
			obj = new UserService();
			break;
		case "ADMIN":
			obj = new AdminService();
			break;
		case "NOVEL":
			obj = new NovelService();
			break;
		case "CATEGORY":
			obj = new CategoryService();
			break;
		case "SITESETTING":
			obj = new SitesettingService();
			break;
		case "CHAPTER":
			obj = new ChapterService();
			break;
		case "NOVELINFO":
			obj = new NovelinfoService();
			break;

		default:
			break;
		}
		return obj;
	}
}
