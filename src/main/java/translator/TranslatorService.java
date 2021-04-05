package translator;

public class TranslatorService {

    public String translateToTile(String text){
        text = text.replace('ł', '\u00FC');
        text = text.replace('ę', '\u00E9');
        text = text.replace('ą', '\u00E2');
        text = text.replace('Ó', '\u00C9');
        text = text.replace('ż', '\u00E6');
        text = text.replace('ć', '\u00E1');
        text = text.replace('ź', '\u00ED');
        text = text.replace('ó', '\u00F3');
        text = text.replace('ń', '\u00F1');
        text = text.replace('Ń', '\u00D1');
        text = text.replace('ś', '\u00FA');
        return text;
    }

    public String translateFromTile(String text){
        text = text.replace('\u00FC', 'ł');
        text = text.replace('\u00E9', 'ę');
        text = text.replace('\u00E2', 'ą');
        text = text.replace('\u00C9', 'Ó');
        text = text.replace('\u00E6', 'ż');
        text = text.replace('\u00E1', 'ć');
        text = text.replace('\u00ED', 'ź');
        text = text.replace('\u00F3', 'ó');
        text = text.replace('\u00F1', 'ń');
        text = text.replace('\u00D1', 'Ń');
        text = text.replace('\u00FA', 'ś');
        return text;
    }
}
