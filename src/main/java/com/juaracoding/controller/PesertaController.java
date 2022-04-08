package com.juaracoding.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.juaracoding.model.Peserta;
import com.juaracoding.repository.PesertaRepository;
import com.juaracoding.utility.FileUtility;

@RestController
public class PesertaController {

	@Autowired
	PesertaRepository pesertaRepository;
	
	@GetMapping("/")
	private List<Peserta> getAll() {
		return pesertaRepository.findAll();
	}
	
	@PostMapping("/")
	private String savePeserta(@RequestParam(name="file") MultipartFile file, 
			@RequestParam("data") String data) throws IOException {
		Gson gson = new Gson();
		Peserta peserta = gson.fromJson(data, Peserta.class);
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uploadDir="foto-peserta";
		FileUtility.simpanFile(uploadDir, fileName, file);
		
		peserta.setGambar(fileName);
		
		pesertaRepository.save(peserta);
		
		return "Berhasil menyimpan dokumen";
		
	}
}
