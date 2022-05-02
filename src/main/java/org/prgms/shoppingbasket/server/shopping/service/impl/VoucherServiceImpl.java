package org.prgms.shoppingbasket.server.shopping.service.impl;

import java.util.List;

import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.prgms.shoppingbasket.server.shopping.service.VoucherService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

	private final VoucherRepository voucherRepository;

	@Override
	public Voucher createVoucher(String voucherType, int value, String description) {
		final Voucher voucher = VoucherType.valueOf(voucherType).create(value, description);
		return voucherRepository.save(voucher);
	}

	@Override
	public List<Voucher> getAllVouchers() {
		return voucherRepository.findAll();
	}
}
