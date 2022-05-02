package org.prgms.shoppingbasket.server.shopping.service;

import java.util.List;

import org.prgms.shoppingbasket.server.shopping.entity.Voucher;

public interface VoucherService {

	Voucher createVoucher(String voucherType, int value, String description);

	List<Voucher> getAllVouchers();
}
