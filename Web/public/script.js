function validateInput() {
        var input = document.getElementById('radiusInput');
        if (input.value < 0) {
            showMessage('Радиус должен быть положительным числом!');
            input.value = '';
        }
    }

    function calculate() {
        var radius = document.getElementById('radiusInput').value;

        if (!radius) {
            showMessage('Введите радиус!');
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://10.0.2.2:4987/circle/calculate", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    try {
                        var response = JSON.parse(xhr.responseText);

                        if (response.error) {
                            showMessage('Ошибка: ' + response.error);
                        } else {
                            document.getElementById('diameter').textContent = response.diameter;
                            document.getElementById('circumference').textContent = response.circumference;
                            document.getElementById('area').textContent = response.area;
                            document.getElementById('result').style.display = 'block';
                        }
                    } catch (e) {
                        showMessage('Ошибка: Некорректный ответ сервера!');
                    }
                } else {
                    showMessage('Ошибка: Сервер недоступен!');
                }
            }
        };

        xhr.send("radius=" + radius);
    }

     function showMessage(message) {
        if (window.Android) {
            window.Android.showToast(message);
        } else {
            alert(message);
        }
    }