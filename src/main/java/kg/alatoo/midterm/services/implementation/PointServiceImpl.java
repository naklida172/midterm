package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.PointDTO;
import kg.alatoo.midterm.entities.Point;
import kg.alatoo.midterm.mappers.PointMapper;
import kg.alatoo.midterm.repositories.PointRepository;
import kg.alatoo.midterm.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;

    @Override
    public Point createPoint(PointDTO pointDTO) {
        Point point = PointMapper.toEntity(pointDTO);
        return pointRepository.save(point);
    }

    @Override
    public Point getPointById(Long id) {
        return pointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Point not found with id: " + id));
    }

    @Override
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    @Override
    public Point updatePoint(Long id, PointDTO pointDTO) {
        Point existingPoint = getPointById(id);
        existingPoint.setAddress(pointDTO.getAddress());
        existingPoint.setStatus(pointDTO.getStatus());
        existingPoint.setWorkTime(pointDTO.getWorkTime());
        return pointRepository.save(existingPoint);
    }

    @Override
    public void deletePoint(Long id) {
        pointRepository.deleteById(id);
    }
}
