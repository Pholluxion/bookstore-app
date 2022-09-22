import { AppBar, Container, Toolbar, Typography } from "@mui/material";
import React from "react";

import "./App.css";
import Main from "./components/Main";

function App() {

    function imprimirVariables(){
        console.log(process.env.REACT_APP_API_URL);
    }

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Catalogo
          </Typography>
        </Toolbar>
        <Container maxWidth="xl"></Container>
      </AppBar>
      <Main />
    </>
  );
}

export default App;
