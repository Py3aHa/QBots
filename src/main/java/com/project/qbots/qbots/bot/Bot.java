package com.project.qbots.qbots.bot;

import com.project.qbots.qbots.front.Front;
import com.project.qbots.qbots.models.Users;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class Bot extends TelegramLongPollingBot {

    Front front = new Front();

    private String message_now = "", language = "";

//    private Users userProfile = new Users();

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message message = update.getMessage();
            if(update.hasMessage()){                //If a text was sent
                if(message.getText().equals("/start")){             //If it was started

                    execute(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Введите ваше имя")
                    );

                    message_now = "Введите ваше имя";               //Save the last sent message from the bot
                } else{

                    if(message_now.equals("Введите ваше имя")){              //If the last message was "Enter your name"

                        execute(new SendMessage().setChatId(update.getMessage().getChatId())
                                .setText("Введите номер телефона")
                        );

                        message_now = "Введите номер телефона";                 //Save the last sent message from the bot
                    } else if(message_now.equals("Введите номер телефона")){                    //If the last message was "Enter phone number"

                        execute(front.buttons_select_language(
                                message.getChatId()).setText("Вас приветствует онлайн-консультант компании Qbots! Просим Вас выбрать язык интерфейса")
                        );

                        message_now = "Выберите язык";                                  //Save the last sent message from the bot

                    } else if(message_now.equals("menu_1_callback")){                   //If the last message was "Order a callback"

                        String answer = "";

                        if(language.equals("kz")){
                            answer = "Қызығушылық танытқаныңыз үшін рахмет. Жұмыс уақытында компания қызметкері Сіз көрсеткен нөмірге қайта қоңырау шалады";
                        } else{
                            answer = "Благодарим за проявленный интерес. В рабочее время сотрудник компании перезвонит на указанный Вами номер";
                        }

                        execute(new SendMessage().setChatId(update.getMessage().getChatId())
                                .setText(answer)
                        );
                    }
                }
            } else if(update.hasCallbackQuery()){                       //If the button was pressed
                String text_menu = "", description_bots = "", description_callback = "", description_company = "";
                String temp = update.getCallbackQuery().getData();

                if(language.equals("") || (temp.equals("ru") || temp.equals("kz"))){                        //If the language is not selected or a different one was selected, then change the interface
                    language = temp;
                }

                if(language.equals("kz")){
                    text_menu = "Әрекеттердің бірін таңдаңыз";
                    description_bots = "Чат - бот немесе басқаша айтқанда \"виртуалды көмекші\" — бұл тәулігіне 24 сағат, аптасына 7 күн жұмыс істейтін және келесі тапсырмаларды орындайтын әмбебап менеджер:\n" +
                            "Байланыс орталығын 80%-ға дейін түсіріп, стандартты сұрақтарға жауап береді \n" +
                            "Сіздің қызметкерлеріңіздің жұмысын ұйымдастырады және бақылайды\n" +
                            "Шоттарды, тапсырыстарды және жеткізілімдерді басқарады\n" +
                            "Сіздің өніміңізді клиентке ұсынады, тапсырысты рәсімдеуге және оны онлайн төлеуге көмектеседі\n" +
                            "Барлық танымал CRM жүйелерімен интеграция";
                    description_callback = "Егер сіз \"Кері қоңырауға\" тапсырыс бергіңіз келсе, өз Атыңызды және байланыс телефоныңыздың нөмірін жазуыңызды сұраймыз";
                    description_company = "Qbots компаниясы Telegram мессенджеріне арналған кіші жүйелік бағдарламалық қамтамасыз етуді әзірлеумен айналысады.\n" +
                            "Біз мемлекеттік ұйымдар, ұлттық компаниялар сияқты экономиканың 10 саласы үшін 50-ден астам ірі жобаны іске асырдық.\n" +
                            "Мекенжайы: 050000, Алматы қ., Байзақов к-сі, 127, 4 кеңсе,\n" +
                            "БСН: 170540017769\n" +
                            "Е-mail: qbots2020@gmail.com,\n" +
                            "www.qbots.kz\n" +
                            "Байланыс Телефоны: 8 778 349 97 94 Нұрлан Биімбет";
                } else{
                    text_menu = "Выберите одно из действий";
                    description_bots = "Чат-бот или другими словами \"Виртуальный ассистент\" — это универсальный менеджер, который работает 24 часа в сутки, 7 дней в неделю и выполняет следующие задачи:\n" +
                            "Разгрузит контактный центр до 80% и ответит на стандартные вопросы \n" +
                            "Организует и проконтролирует работу ваших сотрудников\n" +
                            "Управляет счетами, заказами и доставкой\n" +
                            "Презентует ваш продукт клиенту, поможет оформить заказ и оплатить его онлайн\n" +
                            "Интеграция со всеми популярными CRM системам";
                    description_callback = "Если Вы хотите заказать \"Обратный звонок\" просим написать Ваше имя и номер контактного телефона";
                    description_company = "Компания Qbots занимается разработкой подсистемного программного обеспечения для мессенджера Telegram. \n " +
                            "Мы реализовали более 50 крупных проектов для 10 отраслей экономики как, государственные организации, национальные компании.\n" +
                            "Адрес: 050000, г. Алматы, ул. Байзакова 127, офис 4,\n" +
                            "БИН: 170540017769\n" +
                            "Е-mail: qbots2020@gmail.com,\n" +
                            "www.qbots.kz\n" +
                            "Контакты: 8 778 349 97 94 Нурлан Биимбет";
                }

                if(message_now.equals("Выберите язык") || update.getCallbackQuery().getData().equals(language)){                //If the language is not selected or a different one was selected, then change the interface
                    message_now = "menu_1";                 //Display buttons with language selection
                    execute(front.buttons_menu_1(
                            update.getCallbackQuery()
                                    .getMessage()
                                    .getChatId(), language)
                            .setText(text_menu)
                    );

                } else{
                    if(update.getCallbackQuery().getData().equals("menu_1_company")){                       //If the "About company" button was selected

                        execute(front.buttons_menu_company(
                                update.getCallbackQuery().getMessage().getChatId()
                        ).setText(text_menu));                                          //Display buttons with a selection of "About us" and "Company News" items

                    } else if(update.getCallbackQuery().getData().equals("menu_1_bots")){                   //If the "About bots" button was selected

                        execute(new SendMessage().setChatId(
                                update.getCallbackQuery()
                                        .getMessage()
                                        .getChatId()
                                )
                                .setText(description_bots)
                        );                                                              //Display the text about bots

                    } else if(update.getCallbackQuery().getData().equals("menu_1_callback")){               //If the "Callback" button was selected

                        message_now = "menu_1_callback";

                        execute(new SendMessage().setChatId(
                                update.getCallbackQuery()
                                        .getMessage()
                                        .getChatId()
                                )
                                .setText(description_callback)
                        );                                                              //Display the text for the user to enter a name and phone number

                    } else if(update.getCallbackQuery().getData().equals("menu_2_company")){                    //If the "About us" button was selected
                        execute(new SendMessage().setText(description_company)
                                .setChatId(update.getCallbackQuery()
                                        .getMessage()
                                        .getChatId()
                                )
                        );                                                              //Display the text about bots
                    }
                }

            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Qbots_Ruzana_bot";
    }

    @Override
    public String getBotToken() {
        return "1711508688:AAE-RcieXHIm5fXBCuIa4A-WoMGK-LC1Mps";
    }
}
