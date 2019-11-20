package batch.fileToDB;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonAvgProcess implements ItemProcessor<Person, Person>{
	@Override
	public Person process(final Person person) throws Exception {
		final int korean = person.getKorean();
		final int english = person.getEnglish();
		final int math = person.getMath();
		
		final double avg = (korean + english + math) / 3;
		person.setAvg(avg);
		return person;
	}
}
