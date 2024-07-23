package com.ms.user.model.address;

import jakarta.validation.constraints.NotBlank;

public class ViaCepResponse {
	@NotBlank
	private String cep;
	@NotBlank
	private String logradouro;
	private String complemento;
	@NotBlank
	private String bairro;
	@NotBlank
	private String localidade;
	@NotBlank
	private String uf;

	public ViaCepResponse(@NotBlank String cep, @NotBlank String logradouro, String complemento,
			@NotBlank String bairro, @NotBlank String localidade, @NotBlank String uf) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
