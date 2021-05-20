package com.example.test.repository;

import com.example.test.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    public List<Book> findByYear(String year);
    public List<Book> findByBno(String bno);
    @Query(value = "select * from book where " +
            "if(?1 !='',bno=?1,1=1) " +
            "and if(?2 !='',btype=?2,1=1) " +
            "and if(?3 !='',bname like %?3%,1=1) " +
            "and if(?4 !='',press like %?4%,1=1) " +
            "and if(?5 !='',year>=?5,1=1) " +
            "and if(?6 !='',year<=?6,1=1) " +
            "and if(?7 !='',author like %?7%,1=1) " +
            "and if(?8 !='',price>=?8,1=1) " +
            "and if(?9 !='',price<=?9,1=1) " ,
            nativeQuery = true)
    public List<Book> findByCompound(String bno, String btype, String bname, String presss, Integer minYear, Integer maxYear, String author, Double minPrice, Double maxPrice );

    @Query(value = "select * from book where bno in (select bno from record where cno=?1 and return_date is NULL );", nativeQuery = true)
    public List<Book> findByCno(String cno);

    @Modifying
    @Query(value = "select * from book where bno in (select bno from record where cno=?1 and return_date is NULL );", nativeQuery = true)
    public List<Book> borrowBook(String cno, String bno, String id);


//    @Query(value = "select * from book where x1=?1", nativeQuery = true)
//    public List<Book> compoundFind(String bno, String btype, String bname, String press, int minYear, int maxYear, String author, double minPrice, double maxPrice);

}
