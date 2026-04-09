import "./App.css";
import React from "react";
import { Button, TextField } from "@mui/material";

function App() {
  window.oncontextmenu = function (event) {
    event.preventDefault();
    event.stopPropagation();
    return false;
  };

  function fetch_(endpoint, input_, func) {
    fetch(`http://localhost:8088/${endpoint}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        in: input_,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        func(res.out);
      });
  }

  const [search, changeSearch] = React.useState("");
  const [link_, changeLink] = React.useState("");
  const [characterImage, changeCImg] = React.useState("");

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
          onChange={(e) => {
            changeSearch(e.target.value);
          }}
          onKeyDown={(key) => {
            if (key.key === "Enter") {
              fetch_("specific", search, (r) => {
                changeLink(r);
                fetch_("img", r, (r1) => {
                  changeCImg(r1);
                });
              });
            }
          }}
        ></TextField>
        <Button
          variant="contained"
          className="Random"
          color="primary"
          onClick={() => {
            fetch_("random", "", (r) => {
              changeLink(r);
              fetch_("img", r, (r1) => {
                changeCImg(r1);
              });
            });
          }}
        >
          랜덤 동캐
        </Button>
      </div>
      <div className="Main">
        <img src={characterImage} className={`pfImage`} alt=""></img>
        <a href={link_}>{search}</a>
      </div>
    </div>
  );
}

export default App;
