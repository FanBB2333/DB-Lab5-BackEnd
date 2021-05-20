package com.example.test.controller;

import com.example.test.entity.Book;
import com.example.test.entity.Card;
import com.example.test.entity.Record;
import com.example.test.repository.BookRepository;
import com.example.test.repository.RecordRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/book")
public class BookHandler {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecordRepository recordRepository;

    @RequestMapping("/findAll")
    public List<Book> findAdd(){
        return bookRepository.findAll();

    }

//    @RequestMapping("/findbno/{bno}/{btype}")
//    public List<Book> findByBno(@PathVariable("bno") String bno, @PathVariable("btype") String btype){
    @RequestMapping("/findBy")
    public List<Book> findByBno(@RequestParam(value = "bno", required = false, defaultValue = "") String bno,
                                @RequestParam(value = "btype", required = false, defaultValue = "") String btype,
                                @RequestParam(value = "bname", required = false, defaultValue = "") String bname,
                                @RequestParam(value = "press", required = false, defaultValue = "") String press,
                                @RequestParam(value = "minYear", required = false, defaultValue = "") Integer minYear,
                                @RequestParam(value = "maxYear", required = false, defaultValue = "") Integer maxYear,
                                @RequestParam(value = "author", required = false, defaultValue = "") String author,
                                @RequestParam(value = "minPrice", required = false, defaultValue = "") Double minPrice,
                                @RequestParam(value = "maxPrice", required = false, defaultValue = "") Double maxPrice

    ){
        List<Book> books;
        books = bookRepository.findByCompound(bno, btype, bname, press, minYear, maxYear, author, minPrice, maxPrice);
        System.out.println(books);
        return books;
    }

    @PostMapping("/save")
    public String save(@RequestBody Book book){
        List<Book> ifExist = bookRepository.findByBno(book.getBno());
        Book result;
        if(ifExist.isEmpty()){
            result = bookRepository.save(book);
        }
        else{
            Book newValue = ifExist.get(0);
            if(!newValue.getBtype().equals(book.getBtype()) && !book.getBtype().isEmpty() ){
                return "Wrong Type! Bno: " + book.getBno();
            }
            if(!newValue.getBname().equals(book.getBname()) && !book.getBname().isEmpty() ){
                return "Wrong Name! Bno: " + book.getBno();
            }
            if(!newValue.getPress().equals(book.getPress()) && !book.getPress().isEmpty() ){
                return "Wrong Author! Bno: " + book.getBno();
            }
            if(!newValue.getYear().equals(book.getYear()) && !book.getYear().isEmpty() ){
                return "Wrong Year! Bno: " + book.getBno();
            }
            if(!newValue.getAuthor().equals(book.getAuthor()) && !book.getAuthor().isEmpty() ){
                return "Wrong Author! Bno: " + book.getBno();
            }
            newValue.setStock(newValue.getStock() + 1);
            newValue.setTotal(newValue.getTotal() + 1);
            result = bookRepository.save(newValue); // Update the value
        }
        if(result != null){
            return "success!";
        }
        else{
            return "failed";
        }
    }

    @RequestMapping("/findByCno")
    public List<Book> findByCno(@RequestParam(value = "cno", required = true) String cno){
        List<Book> books;
        books = bookRepository.findByCno(cno);
        return books;
    }

    @RequestMapping("/borrow")
    public String borrowBook(@RequestParam(value = "cno", required = true) String cno,
                                @RequestParam(value = "borrowbno", required = true) String bno,
                                @RequestParam(value = "id", required = true) String id
    ){
        List<Book> toBeBorrowed = bookRepository.findByBno(bno);
        Book result;
        if(toBeBorrowed.isEmpty()){
            return "Can't find the specific book!";
        }
        else{
            Book newValue = toBeBorrowed.get(0);
            if(newValue.getStock() == 0){
                return "No Stock!";
            }
            newValue.setStock(newValue.getStock() - 1);
            result = bookRepository.save(newValue); // Update the value
            Record recordToBeSaved = new Record();
            Timestamp d = new Timestamp(System.currentTimeMillis());
            recordToBeSaved.setBno(bno);
            recordToBeSaved.setCno(cno);
            recordToBeSaved.setBorrow_date(d);
            recordToBeSaved.setId(id);
            recordRepository.save(recordToBeSaved);
            return "Successfully added borrow record!";
        }

    }

    @RequestMapping("/return")
    public String borrowBook(@RequestParam(value = "cno", required = true) String cno,
                             @RequestParam(value = "borrowbno", required = true) String bno
//                             @RequestParam(value = "date", required = true) Timestamp borrowDate
    ){

        List<Record> toBeReturned = recordRepository.findByCnoAndBno(cno, bno);
//        List<Record> toBeReturned = recordRepository.findByCnoAndBnoAndBorrow_date(cno, bno, borrowDate);
        if(toBeReturned.isEmpty()){
            return "Can't find the specific book!";
        }
        for (Record temp : toBeReturned) {
            temp.setReturn_date(new Timestamp(System.currentTimeMillis()));
            recordRepository.save(temp);
        }

        List<Book> toBeAdded = bookRepository.findByBno(bno);
        Book newValue = toBeAdded.get(0);
        newValue.setStock(newValue.getStock() + 1);
        bookRepository.save(newValue); // Update the value


        return bno;

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/root/temp";
//        String filePath = "C:\\Users\\25040\\AppData\\Local\\Temp\\tomcat.8080.9326923898146268262\\work\\Tomcat\\localhost\\ROOT";

        int index=fileName.lastIndexOf(".");
        String tail=fileName.substring(index); //获取后缀名
        String uuidFileName= UUID.randomUUID().toString().replace("-","") + tail;
        File dest = new File(filePath + uuidFileName);
        BufferedReader reader = null;

        try {
            file.transferTo(dest);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dest));
            String line = null;
            while((line = bufferedReader.readLine())!=null) {
                String[] strsplit = line.split(",");
                Double price = Double.parseDouble(strsplit[6]);
                Integer items = Integer.parseInt(strsplit[7]);

                List<Book> ifExist = null;
                ifExist = bookRepository.findByBno(strsplit[0]);
                Book result;
                Book book = new Book();
                if(ifExist.isEmpty()){
                    book.setBno(strsplit[0]);
                    book.setBtype(strsplit[1]);
                    book.setBname(strsplit[2]);
                    book.setPress(strsplit[3]);
                    book.setYear(strsplit[4]);
                    book.setAuthor(strsplit[5]);
                    book.setPrice(price);
                    book.setTotal(items);
                    book.setStock(items);
                    result = bookRepository.save(book);
                }
                else{
                    Book newValue = ifExist.get(0);
                    if(!newValue.getBtype().equals(strsplit[1]) ){
                        return "Wrong type! Bno: " + newValue.getBno();
                    }
                    if(!newValue.getBname().equals(strsplit[2]) ){
                        return "Wrong name! Bno: " + newValue.getBno();
                    }
                    if(!newValue.getPress().equals(strsplit[3]) ){
                        return "Wrong Press! Bno: " + newValue.getBno();
                    }
                    if(!newValue.getYear().equals(strsplit[4]) ){
                        return "Wrong Year! Bno: " + newValue.getBno();
                    }
                    if(!newValue.getAuthor().equals(strsplit[5]) ){
                        return "Wrong Author! Bno: " + newValue.getBno();
                    }
                    newValue.setStock(newValue.getStock() + items);
                    newValue.setTotal(newValue.getTotal() + items);
                    result = bookRepository.save(newValue); // Update the value
                }
            }
            return "上传成功";
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return "上传失败！";
    }




}