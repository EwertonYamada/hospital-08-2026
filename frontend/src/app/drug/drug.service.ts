import { inject, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

export interface Drug {
  id: number,
  code: string,
  name: string,
  value: number,
  stock: number,
}
