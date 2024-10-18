package br.org.serratec.FinalAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.FinalAPI.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
}
