<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>On-Line Shop</title>
    <style>
        h1 {
            margin: auto;
            width: 20%;
        }

        #products {
            margin: auto;
            width: 80%;
            padding: 10px;
        }

        .product {
            display: inline-grid;
            height: 250px;
            width: 250px;
            margin: 20px;
            padding: 5px;
        }

        img {
            height: 250px;
            width: 250px;
        }

        #payment {
            margin: auto;
            text-align: center;
        }

        .paymentMode {
            font-weight: bold;
        }

        #nameComments {
            width: 25%;
            margin: auto;
            padding: 20px;
            display: block;

        }

        #block {
            margin: auto;
            width: 10%;
            display: block;
        }

        #pay {
            padding: 20px;
        }

        .button {
            border-radius: 5px;
            height: 20px;
            width: 50px;
            font-weight: bold;
            text-transform: capitalize;
        }

    </style>
</head>
<body>
<h1>FORM Product Available</h1>
<form name="loginForm" method="post" action="shopServlet">
    <div id="products">
        <label class="product"><img
                src="https://cdn.pixabay.com/photo/2013/09/16/22/23/sheep-183057_960_720.png"><input
                type="checkbox" name="checkBox" value="lamb"></label>
        <label class="product"><img
                src="https://cdn.pixabay.com/photo/2016/03/31/21/22/africa-1296386_960_720.png"><input
                type="checkbox" name="checkBox" value="giraffe"></label>
        <label class="product"><img
                src="data:image/png;base64,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"><input
                type="checkbox" name="checkBox" value="dog"></label>
        <label class="product"><img
                src="https://cdn.pixabay.com/photo/2016/03/31/21/12/cartoon-1296251_960_720.png"><input
                type="checkbox" name="checkBox" value="cat"></label>
        <label class="product"><img
                src="https://cdn.pixabay.com/photo/2017/02/13/16/36/elephant-2063065_960_720.png"><input
                type="checkbox" name="checkBox" value="elephant"></label>
    </div>


    <div id="payment"><h2>Payment Mode</h2>
        Cash <input class="paymentMode" type="radio" name="paymentMethod" value="cash" checked="checked">
        CreditCard <input class="paymentMode" type="radio" name="paymentMethod" value="CreditCard">
        PayPal <input class="paymentMode" type="radio" name="paymentMethod" value="PayPal">
    </div>


    <div id="nameComments">
        <label>Name Client</label>
        <div id="nameClient">
            <input type="text" name="nameClient" placeholder="Name" maxlength="10">
        </div>
        <label>Comments</label>
        <div id="comments">
            <textarea rows="4" cols="50" name="comment" maxlength="200"></textarea>
        </div>
    </div>

    <div id="block">
        <div id="quantity">
            Quantity <select name="quantity">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
        </div>
        <div id="pay">
            <input class="button" type="submit" id="send" value="send">
            <%--<input class="button" type="button" id="del" value="del">--%>
        </div>
    </div>
</form>
</body>
</html>
