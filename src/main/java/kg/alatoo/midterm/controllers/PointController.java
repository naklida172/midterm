package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.dtos.PointDTO;
import kg.alatoo.midterm.entities.Point;
import kg.alatoo.midterm.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping
    public Point createPoint(@RequestBody PointDTO pointDTO) {
        return pointService.createPoint(pointDTO);
    }

    @GetMapping("/{id}")
    public Point getPointById(@PathVariable Long id) {
        return pointService.getPointById(id);
    }

    @GetMapping
    public List<Point> getAllPoints() {
        return pointService.getAllPoints();
    }

    @PutMapping("/{id}")
    public Point updatePoint(@PathVariable Long id, @RequestBody PointDTO pointDTO) {
        return pointService.updatePoint(id, pointDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePoint(@PathVariable Long id) {
        pointService.deletePoint(id);
    }
}
