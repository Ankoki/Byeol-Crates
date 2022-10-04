package com.ankoki.bcrates.commands.conveters;

import com.ankoki.byeol.commands.ArgumentConverter;
import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.api.crates.CrateType;

public class CrateTypeConverter extends ArgumentConverter<CrateType> {

	@Override
	public CrateType convert(String s) {
		return ByeolCrates.getPlugin(ByeolCrates.class).getHandler().getCrateTypeById(s);
	}

	@Override
	public Class<? extends CrateType> getReturnType() {
		return CrateType.class;
	}
}
