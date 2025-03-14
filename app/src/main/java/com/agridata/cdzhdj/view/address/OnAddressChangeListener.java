package com.agridata.cdzhdj.view.address;

import com.agridata.cdzhdj.data.law.EnforcementData;

public interface OnAddressChangeListener {
	void onAddressChange(String address, int id, int type, EnforcementData.RegionBean regionBean);

	void onDismiss(int type);
}
