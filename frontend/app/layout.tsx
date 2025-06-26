import React from "react";
import { Metadata } from "next";
import { Roboto } from "next/font/google";
import "@radix-ui/themes/styles.css";
import "./my-style.css"
import { Theme } from "@radix-ui/themes";

const roboto = Roboto({
    subsets: ['latin']
});

export const metadata: Metadata = {
    title: 'temp-files-hoster',
    description: 'Temporary files hoster',
};

export default function RootLayout({
    children
}: {
    children: React.ReactNode
}) {
    return (
        <html lang="en" className={roboto.className}>
            <body>
                <Theme appearance="light">
                    {children}
                </Theme>
            </body>
        </html>
    );
};
