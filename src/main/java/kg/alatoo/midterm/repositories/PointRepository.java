package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.Point;


@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

}
