package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.PointDTO;
import kg.alatoo.midterm.entities.Point;
import kg.alatoo.midterm.exceptions.ResourceNotFoundException;
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
        if (pointDTO == null) {
            throw new IllegalArgumentException("PointDTO cannot be null.");
        }

        Point point = PointMapper.toEntity(pointDTO);
        return pointRepository.save(point);
    }

    @Override
    public Point getPointById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Point ID cannot be null.");
        }

        return pointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Point not found with id: " + id));
    }

    @Override
    public List<Point> getAllPoints() {
        List<Point> points = pointRepository.findAll();
        if (points.isEmpty()) {
            throw new ResourceNotFoundException("No points found.");
        }
        return points;
    }

    @Override
    public Point updatePoint(Long id, PointDTO pointDTO) {
        if (id == null || pointDTO == null) {
            throw new IllegalArgumentException("Point ID and PointDTO cannot be null.");
        }

        Point existingPoint = getPointById(id);
        existingPoint.setAddress(pointDTO.getAddress());
        existingPoint.setStatus(pointDTO.getStatus());
        existingPoint.setWorkTime(pointDTO.getWorkTime());
        return pointRepository.save(existingPoint);
    }

    @Override
    public void deletePoint(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Point ID cannot be null.");
        }

        if (!pointRepository.existsById(id)) {
            throw new ResourceNotFoundException("Point not found with id: " + id);
        }

        pointRepository.deleteById(id);
    }
}
