<html>
<head>
    <title>Quotes</title>
    <style>
        .header {
            margin: 5rem;
        }
        .main {
            margin: 1rem;
            display: flex;
            flex-flow: row wrap-reverse;
        }
        .element {
            margin: 1rem;
        }
        .main > .quotes {
            flex: auto;
            margin-left: auto;
            margin-right: 1rem;
        }
        .quote {
            display: flex;
            flex-flow: column;
            padding: 1rem;
            margin: 1rem;
            border: 1px solid;
        }
        .vote {
            margin: 5px;
        }
        .user {
            padding: 1rem;
            border: 1px solid;
            display: flex;
        }
    </style>
    <script>
        async function incrementVote(quoteId) {
            let response = await  fetch('/increment?quoteId=' + quoteId, {method:'POST'});
            if (!response.ok) {
                alert("Error HTTP: " + response.status + "\n" + response.text());
            } else {
                window.location.href = '.';
            }
        }
        async function decrementVote(quoteId) {
            let response = await fetch('/decrement?quoteId=' + quoteId, {method:'POST'});
            if (!response.ok) {
                alert("Error HTTP: " + response.status);
            } else {
                window.location.href = '.';
            }
        }
    </script>
</head>
<body>
    <div>
        <a href="/" class="header">Top 10</a>
        <a href="/flop" class="header">Flop 10</a>
    </div>
    <div class="main">
        <div class="quotes">
            <div class="quote">Random quotes:
                <div>${randomQuote.getContent()}</div>
            </div>
            ${quotes}
        </div>
        <div class="user">${user}</div>
    </div>

</body>
</html>