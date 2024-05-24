package com.ms.user.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms.user.infra.security.AddressCrypto;
import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.address.AddressModel;
import com.ms.user.model.address.ViaCepResponse;
import com.ms.user.repository.AddressRepository;


@Service
public class AddressService {
	@Autowired
	public AddressRepository addressRepository;
	@Autowired
	public AddressCrypto addressCrypto;
	
	private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";
	
	public AddressModel insertAddress(AddressDTO addressDTO)  {
		var encryptedAddressData = this.addressCrypto.cryptoAddressData(addressDTO);
		AddressModel newAddress = new AddressModel(encryptedAddressData);
		return this.addressRepository.insert(newAddress);
	}
	public AddressDTO addressByCPF(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(String.format(VIACEP_URL, cep),ViaCepResponse.class);	
	if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
		
		ViaCepResponse viaCepResponse = response.getBody();
        AddressDTO address = new AddressDTO();

        address.setStreet(viaCepResponse.getLogradouro());
        address.setComplement(viaCepResponse.getComplemento());
        address.setNeighborhood(viaCepResponse.getBairro()); 
        address.setCity(viaCepResponse.getLocalidade()); 
        address.setState(viaCepResponse.getUf()); 
        address.setZipCode(cep); 
        return address;
    } else {
        throw new RuntimeException("CEP não encontrado ou erro na consulta.");
    }
}
}
