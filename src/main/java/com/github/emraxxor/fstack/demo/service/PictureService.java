package com.github.emraxxor.fstack.demo.service;

import com.github.emraxxor.fstack.demo.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import com.github.emraxxor.fstack.demo.data.type.Picture;

@Service
public class PictureService extends BasicServiceAdapter<Picture, String, PictureRepository> {
}
