package com.ankoki.bcrates.api.animations;

import com.ankoki.bcrates.misc.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultAnimations {

	private static ItemStack WHITE_PANE = ItemUtils.getBlank(Material.WHITE_STAINED_GLASS_PANE),
							PURPLE_PANE = ItemUtils.getBlank(Material.PURPLE_STAINED_GLASS_PANE);

	public static final CrateAnimation SWIPE = new CrateAnimationBuilder()
			.setShape(1, "pwwwwwwww",
					"pwwwwwwww",
					"pwwwwwwww")
			.setShape(2, "wpwwwwwww",
					"wpwwwwwww",
					"wpwwwwwww")
			.setShape(3, "pwpwwwwww",
					"pwpwwwwww",
					"pwpwwwwww")
			.setShape(4, "wpwpwwwww",
					"wpwpwwwww",
					"wpwpwwwww")
			.setShape(5, "pwpwpwwww",
					"pwpwpwwww",
					"pwpwpwwww")
			.setShape(6, "wpwpwpwww",
					"wpwpwpwww",
					"wpwpwpwww")
			.setShape(7, "pwpwpwpww",
					"pwpwpwpww",
					"pwpwpwpww")
			.setShape(8, "wpwpwpwpw",
					"wpwpwpwpw",
					"wpwpwpwpw")
			.setShape(9, "pwpwpwpwp",
					"pwpwpwpwp",
					"pwpwpwpwp")
			.setShape(10, "wpwpppwpw",
					"wpwpppwpw",
					"wpwpppwpw")
			.setShape(11, "wwpppppww",
					"wwpppppww",
					"wwpppppww")
			.setShape(12, "wwppappww",
					"wwppappww",
					"wwppappww")
			.setShape(13, "wwpprppww",
					"wwppappww",
					"wwppappww")
			.setRowCount(3)
			.setTicksBetween(10)
			.setShapeItem('w', ItemUtils.getBlank(Material.WHITE_STAINED_GLASS_PANE))
			.setShapeItem('p', ItemUtils.getBlank(Material.PURPLE_STAINED_GLASS_PANE))
			.setShapeItem('a', null)
			.setShapeItem('r', CrateAnimation.RESULT_FILLER)
			.build();

	public static final CrateAnimation PORTAL = new CrateAnimationBuilder()
			.setShape(1, 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g')
			.setShape(2, 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(3, 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(4, 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(5, 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(6, 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(7, 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(8, 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p')
			.setShape(9, 'p', 'g', 'g', 'g', 'g', 'p', 'p', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'p', 'p', 'g', 'g', 'g', 'g', 'p')
			.setShape(10, 'p', 'g', 'g', 'g', 'p', 'p', 'p', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'p', 'p', 'p', 'g', 'g', 'g', 'p')
			.setShape(11, 'p', 'g', 'g', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'g', 'g', 'p')
			.setShape(12, 'p', 'g', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'g', 'p')
			.setShape(13, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(14, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(15, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'g', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'g', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(16, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(17, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(18, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(19, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(20, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(21, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'g', 'g', 'g', 'g', 'g', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(22, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'g', 'g', 'g', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(23, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'g', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setShape(24, 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'a', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
									'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p')
			.setRowCount(5)
			.setTicksBetween(5)
			.setShapeItem('g', ItemUtils.getBlank(Material.GRAY_STAINED_GLASS_PANE))
			.setShapeItem('p', ItemUtils.getBlank(Material.PURPLE_STAINED_GLASS_PANE))
			.setShapeItem('a', CrateAnimation.RESULT_FILLER)
			.build();

}
