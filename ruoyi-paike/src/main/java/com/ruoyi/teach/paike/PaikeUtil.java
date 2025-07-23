package com.ruoyi.teach.paike;

import com.ruoyi.teach.paike.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaikeUtil {

	public static void main(String[] args) {
//        Long num = Long.parseLong("111111111111111111111111111111111111111111111111111111111111111", 2);
//        Long num = -1L;
//        System.out.println(getOneCount(num));
		System.out.println(toBinaryString(parseTime("1-1、1-2、2-2、3-3、4-4、5-5", 5, 7)));
	}

	public static int getOneCount(Long num) {
		int count = 0;
		for (int i = 0; i < 64; i++) {
			if ((num & 1) == 1) {
				count++;
			}
			num = num >> 1;
		}
		return count;
	}

	public static int getOneCount(String times) {
//        int count = 0;
//        for (int i = 0; i < times.length(); i++) {
//            if (times.charAt(i) == '1') {
//                count++;
//            }
//        }
//        return count;
		return getOneCount(times, 0, times.length());
	}

	public static int getOneCount(String times, int start, int end) {
		int count = 0;
		for (int i = start; i < end; i++) {
			if (times.charAt(i) == '1') {
				count++;
			}
		}
		return count;
	}

	public static int getZeroCount(Long num) {
		return 64 - getOneCount(num);
	}

	public static int getZeroCount(String times) {
		int count = 0;
		for (int i = 0; i < times.length(); i++) {
			if (times.charAt(i) == '0') {
				count++;
			}
		}
		return count;
	}

	public static String formatTime(long time, int weekdays, int timeOfDay) {
		String sb = toBinaryString(time, weekdays * timeOfDay);
		List<String> days = new ArrayList<>();
		for (int i = 0; i < weekdays; i++) {
			int start = i * timeOfDay;
			String s = sb.substring(start, start + timeOfDay);
			for (int j = 0; j < s.length(); j++) {
				if (s.charAt(j) == '1') {
					days.add(String.format("%d-%d", i + 1, j + 1));
				}
			}
		}
//        return days.stream().collect(Collectors.joining("，"));
		return days.stream().collect(Collectors.joining("、"));
	}

	public static String formatTime(String str) {
		if(str == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String[] times = str.split(";");
		for (int i = 0; i < times.length; i++) {
			String time = times[i];
			for (int j = 0; j < time.length(); j++) {
				if (time.charAt(j) == '1') {
					sb.append(i + 1).append("-").append(j + 1).append("、");
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static long parseTime(String timesPres, int weekdays, int timeOfDay) {
		long times = ~(~0L << weekdays * timeOfDay);
		if (StringUtils.isBlank(timesPres)) {
			return times;
		}
		for (String time : timesPres.split("、")) {
			String[] ss = time.split("-");
			Integer weekday = Integer.parseInt(ss[0]);
			Integer unit = Integer.parseInt(ss[1]);
			long t = 1;
			t = t << (weekdays - weekday) * timeOfDay;
			t = t << timeOfDay - unit;
			t = ~t;
			times = times & t;
		}
		return times;
	}

	public static String toBinaryString(long time) {
		return toBinaryString(time, 5, 7);
	}

	public static String toBinaryString(long time, int length) {
		StringBuilder sb = new StringBuilder(Long.toBinaryString(time));
		while (sb.length() < length) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}

	public static String toBinaryString(long time, int weekdays, int timeOfDay) {
		String str = toBinaryString(time, weekdays * timeOfDay);
		StringBuilder sb = new StringBuilder(str);
		for (int i = weekdays - 1; i > 0; i--) {
			sb.insert(i * timeOfDay, "-");
		}
		return sb.toString();
	}

	public static TimeConfig getTimeConfig() {
		TimeConfig config = new TimeConfig();
		config.setWeekdays(5);
		config.setMorning(4);
		config.setAfternoon(3);
		config.setEvening(0);
		long time = 1;
		for (int i = 0; i < config.getTimeLength(); i++) {
			time = (time << 1) + 1;
		}
		config.setTimeOfWeek(time);
		return config;
	}

	public static List<CourseConfig> getCourseConfigs() {
		List<CourseConfig> configs = new ArrayList<>();
		configs.add(new CourseConfig("体育", Long.valueOf("11100001110000111000011100001110000", 2)));
		return configs;
	}

	public static List<AdminclassConfig> getAdminclassConfigs() {
		List<AdminclassConfig> configs = new ArrayList<>();
		long time = Long.valueOf("00111110111111011111101111111111111", 2);
		configs.add(new AdminclassConfig("高一1班", time));
		configs.add(new AdminclassConfig("高一2班", time));
		configs.add(new AdminclassConfig("高一3班", time));
		configs.add(new AdminclassConfig("高一4班", time));
		configs.add(new AdminclassConfig("高一5班", time));
		configs.add(new AdminclassConfig("高一6班", time));
		configs.add(new AdminclassConfig("高一7班", time));
		configs.add(new AdminclassConfig("高一8班", time));
		return configs;
	}

	public static List<TeacherConfig> getTeacherConfigs() {
		List<TeacherConfig> configs = new ArrayList<>();
		configs.add(new TeacherConfig("陈纪", Long.valueOf("11111111111111111111111111111111100", 2)));
		return configs;
	}

	public static Integer getHour(String hours) {
		if (hours.indexOf("+") < 0) {
			return Integer.parseInt(hours);
		}
		String[] ss = hours.split("\\+");
		int hour = Integer.parseInt(ss[0]) + Integer.parseInt(ss[1]) * 2;
		return hour;
	}

	public static List<LessonInfo> getLessonInfos() {
		List<LessonInfo> list = new ArrayList<>();
		list.add(new LessonInfo("高一", "1班", "语文", 8, "陈纪"));
		list.add(new LessonInfo("高一", "1班", "数学", 4, "张芬"));
		list.add(new LessonInfo("高一", "1班", "生命安全", 1, "陈军"));
		list.add(new LessonInfo("高一", "1班", "美术", 2, "刘梅"));
		list.add(new LessonInfo("高一", "1班", "体育", 3, "张雄"));
		list.add(new LessonInfo("高一", "1班", "道法", 2, "苏平"));
		list.add(new LessonInfo("高一", "1班", "光影秀", 1, "罗红"));
		list.add(new LessonInfo("高一", "1班", "形体", 1, "张雄"));
		list.add(new LessonInfo("高一", "1班", "音乐", 2, "詹艺敏"));
		list.add(new LessonInfo("高一", "1班", "写字", 1, "陈纪"));
		list.add(new LessonInfo("高一", "1班", "工艺心健", 1, "张芬"));
		list.add(new LessonInfo("高一", "1班", "童乐ABC", 1, "郭鸣"));
		list.add(new LessonInfo("高一", "1班", "专题教育", 1, "陈纪"));
		list.add(new LessonInfo("高一", "1班", "科学", 1, "潘昌明"));
		list.add(new LessonInfo("高一", "1班", "经典游戏", 1, "张雄"));

		list.add(new LessonInfo("高一", "2班", "语文", 8, "熊丹"));
		list.add(new LessonInfo("高一", "2班", "数学", 4, "张芬"));
		list.add(new LessonInfo("高一", "2班", "生命安全", 1, "陈军"));
		list.add(new LessonInfo("高一", "2班", "美术", 2, "刘梅"));
		list.add(new LessonInfo("高一", "2班", "体育", 3, "张雄"));
		list.add(new LessonInfo("高一", "2班", "道法", 2, "苏平"));
		list.add(new LessonInfo("高一", "2班", "光影秀", 1, "罗红"));
		list.add(new LessonInfo("高一", "2班", "形体", 1, "张雄"));
		list.add(new LessonInfo("高一", "2班", "音乐", 2, "詹艺敏"));
		list.add(new LessonInfo("高一", "2班", "写字", 1, "熊丹"));
		list.add(new LessonInfo("高一", "2班", "工艺心健", 1, "张芬"));
		list.add(new LessonInfo("高一", "2班", "童乐ABC", 1, "郭鸣"));
		list.add(new LessonInfo("高一", "2班", "专题教育", 1, "熊丹"));
		list.add(new LessonInfo("高一", "2班", "科学", 1, "潘昌明"));
		list.add(new LessonInfo("高一", "2班", "经典游戏", 1, "张雄"));

		list.add(new LessonInfo("高一", "3班", "语文", 8, "韩琳"));
		list.add(new LessonInfo("高一", "3班", "数学", 4, "夏晶晶"));
		list.add(new LessonInfo("高一", "3班", "生命安全", 1, "陈军"));
		list.add(new LessonInfo("高一", "3班", "美术", 2, "刘梅"));
		list.add(new LessonInfo("高一", "3班", "体育", 3, "张晓婷"));
		list.add(new LessonInfo("高一", "3班", "道法", 2, "苏平"));
		list.add(new LessonInfo("高一", "3班", "光影秀", 1, "罗红"));
		list.add(new LessonInfo("高一", "3班", "形体", 1, "张晓婷"));
		list.add(new LessonInfo("高一", "3班", "音乐", 2, "詹艺敏"));
		list.add(new LessonInfo("高一", "3班", "写字", 1, "韩琳"));
		list.add(new LessonInfo("高一", "3班", "工艺心健", 1, "夏晶晶"));
		list.add(new LessonInfo("高一", "3班", "童乐ABC", 1, "陈吉"));
		list.add(new LessonInfo("高一", "3班", "专题教育", 1, "韩琳"));
		list.add(new LessonInfo("高一", "3班", "科学", 1, "潘昌明"));
		list.add(new LessonInfo("高一", "3班", "经典游戏", 1, "张晓婷"));
		return list;
	}
}
