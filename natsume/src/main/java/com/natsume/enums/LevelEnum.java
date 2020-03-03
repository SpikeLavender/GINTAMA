package com.natsume.enums;

import lombok.Getter;

@Getter
public enum LevelEnum {

	LEVEL_0 (0, 0.00, 5000.00, 0.1) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_1 (1, 5000.00, 10000.00, 0.11) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_2 (2, 10000.00, 50000.00, 0.12) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_3 (3, 50000.00, 100000.00, 0.13) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_4 (4, 100000.00, 300000.00, 0.14) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_5 (5, 300000.00, 500000.00, 0.15) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_6 (6, 500000.00, 1000000.00, 0.16) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_7 (7, 1000000.00, 2000000.00, 0.17) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_8 (8, 2000000.00, 3000000.00, 0.18) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_9 (9, 3000000.00, 5000000.00,0.185) {
		public int level(Double number) {
			return 0;
		}
	},
	LEVEL_10 (10, 5000000.00, 9000000000000000.00, 0.19) {
		public int level(Double number) {
			return 0;
		}
	};

	private Integer level;

	private Double min;

	private Double max;

	private Double ratio;

	LevelEnum(int level, double min, double max, double ratio) {
		this.level = level;
		this.min = min;
		this.max = max;
		this.ratio = ratio;
	}

	public static Integer getLevel(Double salesVolume) {
		for (LevelEnum value : LevelEnum.values()) {
			if (salesVolume < value.max && salesVolume >= value.min) {
				return value.level;
			}
		}
		return null;
	}

	public static Double getRatio(Double salesVolume) {
		for (LevelEnum value : LevelEnum.values()) {
			if (salesVolume < value.max && salesVolume >= value.min) {
				return value.ratio;
			}
		}
		return 0.0;
	}

	public static Double getRatio(Integer level) {
		for (LevelEnum value : LevelEnum.values()) {
			if (level.equals(value.level)) {
				return value.ratio;
			}
		}
		return 0.0;
	}
}
