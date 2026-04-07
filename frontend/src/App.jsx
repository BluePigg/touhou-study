import "./App.css";
import React from "react";
import { Button, TextField } from "@mui/material";

function App() {
  window.oncontextmenu = function (event) {
    event.preventDefault();
    event.stopPropagation();
    return false;
  };

  const [search, changeSearch] = React.useState("");
  const [v, cValue] = React.useState("");

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
              fetch("http://localhost:8088/specific", {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify({
                  in: search,
                }),
              })
                .then((res) => res.json())
                .then((res) => {
                  cValue(res.out);
                });
            }
          }}
        ></TextField>
        <Button
          variant="contained"
          className="Random"
          color="primary"
          onClick={() => {
            fetch("http://localhost:8088/random", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
            })
              .then((res) => res.json())
              .then((res) => {
                cValue(res.out);
              });
          }}
        >
          랜덤 동캐
        </Button>
      </div>
      <div className="Main">
        <img
          src="https://i.namu.wiki/i/Piyc1W-kB_bCRXFIO9hC5-4Cqtmha2Y-UkC8wP-uIwA29NyzdNyMAvshIL3ymvSaFk5r1jBdGcUyle6T7S-z0rNzhhEU4eUvf9m3fPpgmBVTNKhwo1fv2MOGOxBmbjwiG6T-XOgT0k6NS2QHDVy5JQ.webp"
          className={`pfImage`}
          alt=""
        ></img>
        <a href={v}>{search}</a>
      </div>
    </div>
  );
}

export default App;
