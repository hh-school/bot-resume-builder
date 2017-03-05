import requests
import json

offset = 0
URL = 'https://api.telegram.org/bot' + 'your_token'

def process():
    global offset

    data = {'offset': offset + 1, 'limit': 0, 'timeout': 3}
    request = requests.post(URL + '/getUpdates', data=data)

    print(request.json())
    
    if not request.status_code == 200: return
    if not request.json()['ok']: return

    for update in request.json()['result']:
        offset = update['update_id']

        if 'message' in update:
            message_data = {
                'chat_id': update['message']['chat']['id'],
                'text': "Для продолжения необходимо выбрать ВУЗ",
                'reply_markup': json.dumps({'inline_keyboard':[[{'text':'Выбрать ВУЗ', 'switch_inline_query_current_chat':''}]]})
            }

            request = requests.post(URL + '/sendMessage', data=message_data)
        elif 'inline_query' in update:
            query_data = {
                'inline_query_id': update['inline_query']['id'],
                'results': json.dumps([
                    {'type':'article', 'id':'1', 'title':"МГУ", 'description': "Московский Государственный Университет им. Ломоносова", 'input_message_content':{'message_text':"МГУ им. Ломоносова"}},
                    {'type':'article', 'id':'2', 'title':"МГТУ им. Баумана", 'description': "Московский Государственный Технический Университет им. Баумана", 'input_message_content':{'message_text':"МГТУ им. Баумана"}}])
            }

            request = requests.post(URL + '/answerInlineQuery', data=query_data)

while True:
    try:
        process()
    except KeyboardInterrupt:
        break
