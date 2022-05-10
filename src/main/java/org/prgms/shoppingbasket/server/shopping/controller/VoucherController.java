package org.prgms.shoppingbasket.server.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.prgms.shoppingbasket.server.shopping.dto.VoucherDto;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VoucherController {

	private final VoucherService voucherService;

	@GetMapping("/api/v1/vouchers")
	public List<VoucherDto> productList() {
		return voucherService.getAllVouchers().stream().map(v -> VoucherDto.of(v)).collect(Collectors.toList());
	}

}
