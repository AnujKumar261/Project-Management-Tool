import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import "./index.css";

// OPTIONAL: only import if file exists
// import setJwtToken from "./utils/setJwtToken";

// If JWT file exists, uncomment below safely
// import jwt_decode from "jwt-decode";
// import store from "./store";
// import { SET_CURRENT_USER } from "./actions/types";

// if (localStorage.jwtToken) {
//   setJwtToken(localStorage.jwtToken);
//   const decoded = jwt_decode(localStorage.jwtToken);
//   store.dispatch({
//     type: SET_CURRENT_USER,
//     payload: decoded,
//   });

//   const currentTime = Date.now() / 1000;
//   if (decoded.exp < currentTime) {
//     store.dispatch(logoutUser());
//     window.location.href = "/login";
//   }
// }

ReactDOM.render(<App />, document.getElementById("root"));