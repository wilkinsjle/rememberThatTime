package edu.ucsc.soe.ccs.rememberthattime.nlg;

import java.util.ArrayList;
import java.util.List;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import edu.ucsc.soe.ccs.rememberthattime.ail.*;
import edu.ucsc.soe.ccs.rememberthattime.ail.rummyspecificail.*;

public class NLGEngine {

	Lexicon lexicon;
	NLGFactory nlgFactory;
	Realiser realiser;

	public NLGEngine() {
		lexicon = Lexicon.getDefaultLexicon();
		nlgFactory = new NLGFactory(lexicon);
		realiser = new Realiser(lexicon);	
	}

	public List<String> generateStoryFromAIL(List<AILInstance> ail){
		List<String> story = new ArrayList<String>();
		for(AILInstance ailInstance : ail)
			story.add(generateSentenceFromAILInstance(ailInstance));
		return story;
	}

	private String generateSentenceFromAILInstance (AILInstance aili){

		SPhraseSpec p = nlgFactory.createClause();

		// setting subj, obj and other ail-instance-type-dependent parts...

		if(aili instanceof GameOverEvent){ //subj:game verb:end
			p.setSubject((aili.getSubject().getName()));
			p.setVerb(((GameOverEvent) aili).getVerb());
		}

		else if (aili instanceof DiscardEvent){ // subj:subj verb:discard obj:a card
			p.setSubject(aili.getSubject().getName());
			p.setObject(aili.getObject().getName());
			p.setVerb(((DiscardEvent) aili).getVerb());
		}

		else if (aili instanceof MeldEvent){ // subj:subj verb:make obj:a meld
			p.setSubject(aili.getSubject().getName());
			p.setObject(aili.getObject().getName());
			p.setVerb(((MeldEvent) aili).getVerb());
		}

		else if (aili instanceof LayOffEvent){ // subj:subj verb:lay off obj:a card
			p.setSubject(aili.getSubject().getName());
			p.setObject(aili.getObject().getName());
			p.setVerb(((LayOffEvent) aili).getVerb());
		}

		else if (aili instanceof FacialExpressionAILInstance){// subj:user verb:smile OR verb:stop obj:smile
			p.setSubject(aili.getSubject().getName());
			p.setVerb(aili.getVerb());
			if(((FacialExpressionAILInstance) aili).stopped)
				p.setObject(((FacialExpressionAILInstance) aili).stoppedWhat());
		}

		else if (aili instanceof SpeechAILInstance){
			p.setSubject(aili.getSubject().getName());
			p.setObject(aili.getObject().getName());
			p.setVerb(aili.getVerb());
			p.addComplement(((SpeechAILInstance) aili).getContent());
		}

		else if (aili instanceof ResponseEvent){
			p.setSubject(aili.getSubject().getName());
			p.setObject(aili.getObject().getName());
			p.setVerb(aili.getVerb());
			p.addComplement(": \"" + nlgFactory.createSentence(
					((SpeechAILInstance) aili).getContent())+"\"");
		}

		// the rest (commong for all types of ail instances)...

		p.setFeature(Feature.TENSE, Tense.PAST);
		//		verb.addModifier("quickly");

		String sentence = realiser.realiseSentence(p);

		return sentence;
	}


	public List<String> modifySentencesForCoherency(){
		//TODO
		return null;
	}

	//test method
	public static void main(String[] args) {
		
	}

}
