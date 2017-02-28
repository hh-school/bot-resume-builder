import config
import telebot

bot = telebot.TeleBot(config.token)

@bot.message_handler(content_types=["text"])
def repeat_all_messages(message):
    if message.text != 'qweqeqe':
        bot.send_message(message.chat.id, 'try \'qweqeqe\'')
    else:
        markup = telebot.types.ReplyKeyboardMarkup(one_time_keyboard=True, resize_keyboard=True)
        for item in ['1', '2', '3']:
            markup.add(item)
        bot.send_message(message.chat.id, 'are u sure?', reply_markup = markup)


if __name__ == '__main__':
    bot.polling(none_stop=True)
