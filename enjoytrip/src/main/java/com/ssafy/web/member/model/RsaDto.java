package com.ssafy.web.member.model;

import lombok.Builder;

public class RsaDto {
	private String modulus, exponent;

	@Override
	public String toString() {
		return "RsaDto [modulus=" + modulus + ", exponent=" + exponent + "]";
	}

	@Builder
	public RsaDto(String modulus, String exponent) {
		super();
		this.modulus = modulus;
		this.exponent = exponent;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	
}
