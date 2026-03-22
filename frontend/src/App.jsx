import "./App.css";
import React from "react";
import { Button, TextField } from "@mui/material";

function App() {
  window.oncontextmenu = function (event) {
    event.preventDefault();
    event.stopPropagation();
    return false;
  };
  return (
    <div className="App">
      <div className="NavBar">
        <img
          src={`${process.env.PUBLIC_URL}/images/fumo.png`}
          className="fumo"
          alt="fumo"
        ></img>
        <TextField
          variant="filled"
          className="SearchBar"
          label="동캐 이름 입력 (ex: 하쿠레이레이무)"
        ></TextField>
        <Button variant="contained" className="Random" color="primary">
          랜덤 동캐
        </Button>
      </div>
      <div className="Main">
        <img
          src="https://i.namu.wiki/i/wgzMvFitt_Ccx3R_5TSQgvN94SCkeLH326fQcjGTVxfgnul34UVJ6Sp5Skhz_8HftWrSf2OZjNQp1NjiF2wNjYT2Le9Atm38_Hj0MQFijIC2uffrZkjR_MncTxRDDUwAydIafQvSJDXqwFH-Suz5Hg.webp"
          className={`pfImage`}
          alt=""
        ></img>
      </div>
    </div>
  );
}

export default App;
