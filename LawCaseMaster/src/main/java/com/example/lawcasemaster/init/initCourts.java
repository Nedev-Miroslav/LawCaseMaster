package com.example.lawcasemaster.init;

import com.example.lawcasemaster.model.entity.Court;
import com.example.lawcasemaster.repository.CourtRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initCourts  implements CommandLineRunner {
    private final CourtRepository courtRepository;

    public initCourts(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        long count = this.courtRepository.count();

        if (count != 0) {
            return;
        }


        Court court = new Court();
        court.setName("SOFIA DISTRICT COURT");
        court.setAddress("54 Tsar Boris III Blvd");
        court.setPhoneNumber("02 8954 300");
        courtRepository.save(court);

        Court court2 = new Court();
        court2.setName("PLOVDIV DISTRICT COURT");
        court2.setAddress("167 6th Septemvri Blvd");
        court2.setPhoneNumber("032 656 325");
        courtRepository.save(court2);

        Court court3 = new Court();
        court3.setName("VARNA DISTRICT COURT");
        court3.setAddress("Vladislav Varnenchik 57 Blvd");
        court3.setPhoneNumber("052 612 189");
        courtRepository.save(court3);

        Court court4 = new Court();
        court4.setName("BURGAS DISTRICT COURT");
        court4.setAddress("101 Aleksandrovska St., 3rd floor");
        court4.setPhoneNumber("056 878 855");
        courtRepository.save(court4);

        Court court5 = new Court();
        court5.setName("STARA ZAGORA DISTRICT COURT");
        court5.setAddress("Metodiy Kusev St 33");
        court5.setPhoneNumber("042 622 949");
        courtRepository.save(court5);

        Court court6 = new Court();
        court6.setName("VELIKO TARNOVO DISTRICT COURT");
        court6.setAddress("Vasil Levski St 16");
        court6.setPhoneNumber("062 615 910");
        courtRepository.save(court6);

        Court court7 = new Court();
        court7.setName("RUSE DISTRICT COURT");
        court7.setAddress("Alexandrovska St 57, 7000 Ruse");
        court7.setPhoneNumber("082 825 428");
        courtRepository.save(court7);

        Court court8 = new Court();
        court8.setName("RAZGRAD DISTRICT COURT");
        court8.setAddress("1 Independence Street");
        court8.setPhoneNumber("084 624 339");
        courtRepository.save(court8);





    }
}
