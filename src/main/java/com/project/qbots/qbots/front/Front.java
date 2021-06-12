package com.project.qbots.qbots.front;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Front {
    private HashMap<String, String> text_kz = new HashMap<>();
    private HashMap<String, String> text_ru = new HashMap<>();

    private String language = "";

    public void add_messages(){
        //<------------------------Kazakh LANGUAGE------------------------>
        text_kz.put("enter_name", "Атыңызды енгізіңіз");
        text_kz.put("enter_phone", "Телефон нөмірін енгізіңіз");

        text_kz.put("language", "Қазақ тілінде");

        text_kz.put("menu_1_company", "Компания туралы");
        text_kz.put("menu_1_bots", "Чат-боттар туралы");
        text_kz.put("menu_1_callback", "Кері қоңырауға тапсырыс беру");

        text_kz.put("menu_2_about_company", "Біз туралы");
        text_kz.put("menu_2_news", "Компания жаңалықтары");

        //<------------------------Russian LANGUAGE------------------------>

        text_ru.put("enter_name", "Введите ваше имя");
        text_ru.put("enter_phone", "Введите номер вашего телефона");
        text_ru.put("language", "На русском языке");

        text_ru.put("menu_1_company", "О компании");
        text_ru.put("menu_1_bots", "О чат-ботах");
        text_ru.put("menu_1_callback", "Заказать обратный звонок");

        text_ru.put("menu_2_about_company", "О нас");
        text_ru.put("menu_2_news", "Новости компании");
    }


                                        //<------------------------SELECT LANGUAGE------------------------>
    public SendMessage buttons_select_language(long chatId){
        add_messages();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();     //Creating a button field
        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();             //Creating buttons for a row

        keyboardButtons.add(new InlineKeyboardButton().setText(text_kz.get("language")).setCallbackData("kz"));         //Add a button for the kazakh language
        keyboardButtons.add(new InlineKeyboardButton().setText(text_ru.get("language")).setCallbackData("ru"));         //Add a button for the russian language

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();               //Creating a row
        rowList.add(keyboardButtons);           //Add a row to the button field

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setReplyMarkup(inlineKeyboardMarkup);
    }


                                        //<------------------------MENU 1------------------------>
                                        //<------------------------About company------------------------>
                                        //<------------------------About bots------------------------>
                                        //<------------------------Callback------------------------>
    public SendMessage buttons_menu_1(long chatId, String lang){

        language = lang;

        String menu_1_company = "", menu_1_bots = "", menu_1_callback = "";

        if(lang.equals("kz")){
            menu_1_company = text_kz.get("menu_1_company");
            menu_1_bots = text_kz.get("menu_1_bots");
            menu_1_callback = text_kz.get("menu_1_callback");
        } else{
            menu_1_company = text_ru.get("menu_1_company");
            menu_1_bots = text_ru.get("menu_1_bots");
            menu_1_callback = text_ru.get("menu_1_callback");
        }

        System.out.println("line 83" + menu_1_company);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();     //Creating a button field

        List<InlineKeyboardButton> keyboardButtonsCompany = new ArrayList<>();             //Creating buttons for a row
        keyboardButtonsCompany.add(new InlineKeyboardButton().setText(menu_1_company).setCallbackData("menu_1_company"));          // about company

        List<InlineKeyboardButton> keyboardButtonsBots = new ArrayList<>();             //Creating buttons for a row
        keyboardButtonsBots.add(new InlineKeyboardButton().setText(menu_1_bots).setCallbackData("menu_1_bots"));                   //about bots

        List<InlineKeyboardButton> keyboardButtonsCallback = new ArrayList<>();             //Creating buttons for a row
        keyboardButtonsCallback.add(new InlineKeyboardButton().setText(menu_1_callback).setCallbackData("menu_1_callback"));       //Callback


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsCompany);
        rowList.add(keyboardButtonsBots);
        rowList.add(keyboardButtonsCallback);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setReplyMarkup(inlineKeyboardMarkup);
    }


                                                        //<------------------------MENU 1------------------------>
                                                        //<------------------------About us------------------------>
                                                        //<------------------------Company News------------------------>

    public SendMessage buttons_menu_company(long chatId){
        String menu_2_company = "", menu_2_news = "";

        if(language.equals("kz")){
            menu_2_company = text_kz.get("menu_2_about_company");
            menu_2_news = text_kz.get("menu_2_news");
        } else{
            menu_2_company = text_ru.get("menu_2_about_company");
            menu_2_news = text_ru.get("menu_2_news");
        }


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();     //Creating a button field

        List<InlineKeyboardButton> keyboardButtonsCompany = new ArrayList<>();             //Creating buttons for a row
        keyboardButtonsCompany.add(new InlineKeyboardButton().setText(menu_2_company).setCallbackData("menu_2_company"));          // about us

        List<InlineKeyboardButton> keyboardButtonsNews = new ArrayList<>();             //Creating buttons for a row
        keyboardButtonsNews.add(new InlineKeyboardButton().setText(menu_2_news).setCallbackData("menu_2_news").setUrl("https://www.qbots.kz/"));                   //company news

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsCompany);
        rowList.add(keyboardButtonsNews);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setReplyMarkup(inlineKeyboardMarkup);

    }
}
