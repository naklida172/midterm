package kg.alatoo.midterm.services;

import kg.alatoo.midterm.dtos.PointDTO;
import kg.alatoo.midterm.entities.Point;

import java.util.List;

public interface PointService {
    Point createPoint(PointDTO pointDTO);
    Point getPointById(Long id);
    List<Point> getAllPoints();
    Point updatePoint(Long id, PointDTO pointDTO);
    void deletePoint(Long id);
}
