package kg.alatoo.midterm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kg.alatoo.midterm.entities.Point;
import kg.alatoo.midterm.repositories.PointRepository;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {

    @Autowired
    private PointRepository pointRepository;

    @GetMapping
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    @GetMapping("/{id}")
    public Point getPointById(@PathVariable Long id) {
        return pointRepository.findById(id).orElseThrow(() -> new RuntimeException("Point not found"));
    }

    @PostMapping
    public Point createPoint(@RequestBody Point point) {
        return pointRepository.save(point);
    }

    @PutMapping("/{id}")
    public Point updatePoint(@PathVariable Long id, @RequestBody Point pointDetails) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException("Point not found"));
        point.setAddress(pointDetails.getAddress());
        point.setStatus(pointDetails.getStatus());
        point.setWorkTime(pointDetails.getWorkTime());
        return pointRepository.save(point);
    }

    @DeleteMapping("/{id}")
    public void deletePoint(@PathVariable Long id) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException("Point not found"));
        pointRepository.delete(point);
    }
}
