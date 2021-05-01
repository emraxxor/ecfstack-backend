package hu.emraxxor.fstack.demo.service;

import org.springframework.stereotype.Service;

import hu.emraxxor.fstack.demo.data.type.Picture;
import hu.emraxxor.fstack.demo.repositories.PictureRepository;

@Service
public class PictureService extends BasicServiceAdapter<Picture, String, PictureRepository> {
}
